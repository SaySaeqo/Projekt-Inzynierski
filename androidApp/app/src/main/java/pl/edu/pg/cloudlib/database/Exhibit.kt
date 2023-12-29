package pl.edu.pg.cloudlib.database

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class Exhibit {
    lateinit var id: String; // id of exhibit
    lateinit var name: String; // name of exhibit
    lateinit var description: String; //short description
    lateinit var extra: Array<ExhibitPair>; //extra parameters
    lateinit var widgets: Array<Widget>;
    lateinit var icon: String; // path to image with icon
    lateinit var location: String; // path to image with location on exhibition

    fun init(
        /*id: String,
        name: String,
        description: String,
        extra: Array<ExhibitPair>,
        widgets: Array<Widget>,
        icon: String,
        location: String*/
    ) {
        this.id = "id"
        this.name = "name"
        this.description = "description"
        this.extra = arrayOf()//extra
        this.widgets = arrayOf()//widgets
        this.icon = "icon"
        this.location = "location"
    }

    override fun toString(): String {
        return "Exhibit(id='$id', name='$name', description='$description', extra=${extra.contentToString()}, widgets=${widgets.contentToString()}, icon='$icon', location='$location')"
    }

    companion object {
        fun fromFirebaseConverter(doc: DocumentSnapshot): Exhibit {
            val newExhibit = Exhibit()
            newExhibit.id = doc.id
            newExhibit.name = doc.data?.get("name").toString()
            newExhibit.description = doc.data?.get("description").toString()
            newExhibit.widgets = Gson().fromJson(doc.data?.get("widgets").toString(), object : TypeToken<Array<Widget>>() {}.type)?: arrayOf()
            newExhibit.extra = Gson().fromJson(doc.data?.get("extra").toString(), object : TypeToken<Array<ExhibitPair>>() {}.type)?: arrayOf()
            newExhibit.icon = doc.data?.get("icon").toString()
            newExhibit.location = doc.data?.get("location").toString()

            return newExhibit
        }
    }

}

class Widget {
    var id: Int = 0;
    lateinit var type: String;
    lateinit var title: String;
    lateinit var data: String;
    lateinit var imagesURLs: Array<String>;

    fun init(
        id: Int,
        type: String,
        title: String,
        data: String,
        imagesURLs: Array<String>
    ) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.data = data;
        this.imagesURLs = imagesURLs;
    }
}

class ExhibitPair {
    lateinit var value: String;
    lateinit var key: String;
    lateinit var linkId: String;

    fun init(value: String, key: String, linkId: String) {
        this.value = value;
        this.key = key;
        this.linkId = linkId;
    }
}