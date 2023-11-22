package pl.edu.pg.cloudlib

import android.os.SystemClock.sleep
import android.util.Log
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DBSingleton private constructor() {
    private val db = Firebase.firestore

    companion object {
        private val dbCollectionName = "exhibits"
        private var instance = DBSingleton()
        const val TAG = "DataBase"
        fun getInstance() : DBSingleton{
            return instance;
        }
    }

    init {
        exhibitTest()
    }

    fun exhibitTest() {

        //userTest()
        add("costam", "opis")
        //sleep(1000)
        delete("costam")
        //sleep(1000)
        delete("costam")
        //sleep(4000)
        getAll()
    }

    fun userTest() {
        // Create a new user with a first and last name
        var user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        // Create a new user with a first, middle, and last name
        user = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912
        )

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
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

    fun getAll() {
        db.collection(dbCollectionName)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "One of all ${document.id} => ${document.data}")
                }
                if(result.isEmpty){
                    Log.d(TAG, "No docs!")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}