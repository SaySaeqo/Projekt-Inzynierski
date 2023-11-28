import firebase from "firebase";
import "firebase/firestore";

let config = {
  databaseURL:
    "https://cloudlib-980ef-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "cloudlib-980ef",
  appId: "1:1043485449559:android:d18b68a50b6cd8a1f0f212",
};

firebase.initializeApp(config);

export default firebase.firestore();
