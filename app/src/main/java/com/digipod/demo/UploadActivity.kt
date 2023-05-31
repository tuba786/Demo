package com.digipod.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digipod.demo.fragments.FragmentHome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_appointment.*
import java.util.*

class UploadActivity : AppCompatActivity() {
    private lateinit var selectFileButton: Button
    private lateinit var uploadButton: Button
    private lateinit var documentRecyclerView: RecyclerView

    private lateinit var storageRef: StorageReference
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userId: String

    private val selectedDocuments: MutableList<Uri> = mutableListOf()
    private lateinit var documentAdapter: DocumentAdapter

    companion object {
        private const val PICK_DOCUMENT_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        storageRef = FirebaseStorage.getInstance().reference
        firestore = FirebaseFirestore.getInstance()

        // Get the current user's ID
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        selectFileButton = findViewById(R.id.select_file_button)
        uploadButton = findViewById(R.id.upload_button)
        documentRecyclerView = findViewById(R.id.document_recycler_view)
        btnBack.setOnClickListener {
            val intent = Intent(this, FragmentHome::class.java)
            startActivity(intent)
        }

        documentAdapter = DocumentAdapter(getDocumentListFromUris(selectedDocuments) as MutableList<Document>)
        documentRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@UploadActivity)
            adapter = documentAdapter
        }

        selectFileButton.setOnClickListener {
            selectDocumentFile()
        }

        uploadButton.setOnClickListener {
            uploadDocuments()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchUploadedDocuments()
    }
    @SuppressLint("Range")
    private fun getFileNameFromUri(uri: Uri): String {
        val contentResolver: ContentResolver = contentResolver
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        var fileName = ""
        cursor?.use {
            if (it.moveToFirst()) {
                fileName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
        return fileName
    }
    private fun getDocumentListFromUris(uris: List<Uri>): List<Document> {
        val documents = mutableListOf<Document>()
        for (uri in uris) {
            val documentName = getFileNameFromUri(uri)
            val document = Document(documentName, uri.toString())
            documents.add(document)
        }
        return documents
    }


    private fun selectDocumentFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf" // Set the desired file type, e.g., PDF files
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Documents"), PICK_DOCUMENT_REQUEST)
    }

    private fun uploadDocuments() {
        if (selectedDocuments.isNotEmpty()) {
            for (documentUri in selectedDocuments) {
                val fileName = UUID.randomUUID().toString()
                val fileRef = storageRef.child("users/$userId/documents/$fileName")

                fileRef.putFile(documentUri)
                    .addOnSuccessListener { taskSnapshot ->
                        // Get the download URL of the uploaded document
                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUri ->
                            saveDocumentUrlToFirestore(fileName, downloadUri.toString())
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Error uploading documents: ${exception.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        } else {
            Toast.makeText(this, "No documents selected.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDocumentUrlToFirestore(fileName: String, downloadUrl: String) {
        val documentRef =
            firestore.collection("users").document(userId).collection("documents").document(fileName)

        val documentData = hashMapOf(
            "downloadUrl" to downloadUrl
        )

        documentRef.set(documentData)
            .addOnSuccessListener {
                Toast.makeText(this, "Documents uploaded successfully.", Toast.LENGTH_SHORT).show()
                fetchUploadedDocuments()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error saving document URLs: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchUploadedDocuments() {
        val documentRef = firestore.collection("users").document(userId).collection("documents")

        documentRef.get()
            .addOnSuccessListener { querySnapshot ->
                val documents = mutableListOf<Document>()
                for (documentSnapshot in querySnapshot.documents) {
                    val documentId = documentSnapshot.id
                    val downloadUrl = documentSnapshot.getString("downloadUrl")
                    if (downloadUrl != null) {
                        val document = Document(documentId, downloadUrl)
                        documents.add(document)
                    }
                }
                displayUploadedDocuments(documents)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error fetching documents: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun displayUploadedDocuments(documents: List<Document>) {
        documentAdapter.apply {
            setDocuments(documents)
            notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_DOCUMENT_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val clipData = data.clipData
                for (i in 0 until clipData!!.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    selectedDocuments.add(uri)
                }
            } else if (data?.data != null) {
                val uri = data.data
                selectedDocuments.add(uri!!)
            }

            documentAdapter.notifyDataSetChanged()
            uploadButton.isEnabled = true
        }
    }

    private inner class DocumentAdapter(private val documents: MutableList<Document>) :
        RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_document, parent, false)
            return DocumentViewHolder(view)
        }

        override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
            val document = documents[position]
            holder.bind(document)
        }

        override fun getItemCount(): Int = documents.size

        fun setDocuments(updatedDocuments: List<Document>) {
            documents.clear()
            documents.addAll(updatedDocuments)
        }

        inner class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val documentNameTextView: TextView = itemView.findViewById(R.id.document_name_text_view)

            fun bind(document: Document) {
                documentNameTextView.text = document.name

                itemView.setOnClickListener {
                    openDocument(document)
                }
            }

            private fun openDocument(document: Document) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(document.downloadUrl)
                startActivity(intent)
            }
        }
    }

    data class Document(val name: String, val downloadUrl: String)
}

