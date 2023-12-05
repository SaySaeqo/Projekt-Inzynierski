//import { initializeApp } from "firebase/app";
//import firebase from "firebase/app";
//import { getFirestore } from "firebase/firestore";
import { initializeApp } from "firebase/app";

//import "firebase";

let config = {
  apiKey: "AIzaSyCd4GmSTcBhnrnyxwAxGz63VeZoBHMu4p8",
  authDomain: "cloudlib-980ef.firebaseapp.com",
  databaseURL:
    "https://cloudlib-980ef-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "cloudlib-980ef",
  storageBucket: "cloudlib-980ef.appspot.com",
  messagingSenderId: "1043485449559",
  appId: "1:1043485449559:web:aa1778ddb811a617f0f212",
  measurementId: "G-P5VP782P9M",
};

const firebaseApp = initializeApp(config);

export default firebaseApp;
