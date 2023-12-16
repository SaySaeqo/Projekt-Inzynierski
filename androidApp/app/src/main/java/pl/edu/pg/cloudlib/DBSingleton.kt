package pl.edu.pg.cloudlib

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.SystemClock.sleep
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

class DBSingleton private constructor() {
    private val db = Firebase.firestore
    private val storage = Firebase.storage

    companion object {
        private val dbCollectionName = "exhibits"
        private var instance = DBSingleton()
        const val TAG = "DataBase"
        fun getInstance() : DBSingleton{
            return instance;
        }
    }

    fun add(name: String, description: String)
    {
        var exhibit = hashMapOf(
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
        var result = db.collection(dbCollectionName).orderBy("name").get();

        return result;
    }

    fun getOne(id: String): Task<DocumentSnapshot> {
        var result = db.collection(dbCollectionName).document(id).get();

        return result
    }

    fun getImage(name: String): StorageReference {
        val imagesRef = storage.getReference("images")
        val imageRef = imagesRef.child(name)

        return imageRef
    }
}
