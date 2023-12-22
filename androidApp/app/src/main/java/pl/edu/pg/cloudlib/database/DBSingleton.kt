package pl.edu.pg.cloudlib.database

import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

object DBSingleton {
    private val db get() = Firebase.firestore
    private val storage get()  = Firebase.storage

    private const val dbCollectionName = "exhibits"
    private const val TAG = "DataBase"

    fun add(name: String, description: String)
    {
        val exhibit = hashMapOf(
            "name" to name,
            "description" to description
        )

        db.collection(dbCollectionName)
            .add(exhibit)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Added ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun delete(name: String) {
        db.collection(dbCollectionName)
            .where(Filter.equalTo("name", name))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "To delete ${document.id} => ${document.data}")
                    db.collection(dbCollectionName)
                        .document(document.id)
                        .delete()
                        .addOnSuccessListener { result1 ->
                            Log.d(TAG, "Deleted doc")
                        }
                        .addOnFailureListener{ e ->
                            Log.w(TAG, "Deleted doc doesn't exist")
                        }
                }
                if(result.isEmpty) {
                    Log.d(TAG, "Deleted doc doesn't exist")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getAll(): Task<QuerySnapshot> {
        val result = db.collection(dbCollectionName).orderBy("name").get();

        return result;
    }

    fun getOne(id: String): Task<DocumentSnapshot> {
        val result = db.collection(dbCollectionName).document(id).get();

        return result
    }

    fun getImage(name: String): StorageReference {
        val imagesRef = storage.getReference("images")
        val imageRef = imagesRef.child(name)

        return imageRef
    }

    fun forAll(function: (Exhibit) -> Unit) {
       getAll().addOnSuccessListener { result ->
                for (document in result) {
                    val exh = Exhibit.fromFirebaseConverter(document)
                    function(exh)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun forOne(id: String, onSuccess: (Exhibit) -> Unit) {
        getOne(id).addOnSuccessListener { result ->
            val exh = Exhibit.fromFirebaseConverter(result)
            onSuccess(exh)
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting document with id ${id}.", exception)
        }
    }

    fun loadImageInto(name: String, imageView: ImageView) {
        val imageRef = getImage(name)
        imageRef.downloadUrl.addOnSuccessListener {
            Glide.with(imageView.context)
                .load(it)
                .into(imageView)
        }.addOnFailureListener(){
            Log.d(TAG, "Failed to load image")
        }
    }
}
