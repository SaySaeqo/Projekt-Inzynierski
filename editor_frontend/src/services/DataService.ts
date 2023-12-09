import {
  addDoc,
  collection,
  deleteDoc,
  doc,
  getDoc,
  getDocs,
  getFirestore,
  orderBy,
  query,
  updateDoc
} from "firebase/firestore";
import firebaseApp from "@/initfirestore";
import { deleteObject, getDownloadURL, getStorage, ref, uploadBytes } from "firebase/storage";
import { Exhibit } from "@/models/Exhibit";
//import { randomUUID } from "crypto";

const db = getFirestore(firebaseApp);
const storage = getStorage(firebaseApp);
const imagesRef = ref(storage, "images");

class DataService {
  private static exists: boolean;
  private static instance: DataService;
  constructor() {
    if (DataService.exists) {
      return DataService.instance;
    }
    DataService.exists = true;
    DataService.instance = this;
    return this;
  }
  async getAll() {
    const sth = [] as Exhibit[];
    sth.pop();
    const querySnap = await getDocs(
      query(collection(db, "exhibits"), orderBy("name"))
    );

    // add each doc to 'countries' array
    querySnap.forEach((doc) => {
      sth.push(new Exhibit(doc.id, doc.data().name));
    });

    return sth;
  }

  async getOne(id: string) {
    const snap = await getDoc(doc(db, "exhibits", id));
    console.log(Exhibit.fromFirebaseConverter(snap.id, snap.data()));
    return Exhibit.fromFirebaseConverter(snap.id, snap.data());
  }

  async create(exhibit: Exhibit) {
    console.log(exhibit.toFirestoreConverter());
    await addDoc(collection(db, "exhibits"), exhibit.toFirestoreConverter());
  }

  async createEmpty() {
    const sth = await addDoc(
      collection(db, "exhibits"),
      new Exhibit().toFirestoreConverter()
    );
    return sth.id;
  }

  async update(exhibit: Exhibit) {
    console.log(exhibit.toFirestoreConverter());
    await updateDoc(
      doc(db, "exhibits", exhibit.id),
      exhibit.toFirestoreConverter()
    );
  }

  async delete(id: string) {
    await deleteDoc(doc(db, "exhibits", id));
  }

  async addImage(exhibitId: string, imageFile: File) {
    //const uuid = crypto.randomUUID();
    const extension = imageFile.name.split(".").pop();
    const name = exhibitId + "_" + "." + extension;
    const fileRef = ref(imagesRef, name);
    uploadBytes(fileRef, imageFile).then(() => {
      console.log("Uploaded a blob or file!");
    });

    return fileRef.name;
  }

  async removeImage(name: string) {
    // Create a reference to the file to delete
    const imageRef = ref(imagesRef, name);
    // Delete the file
    await deleteObject(imageRef);
  }

  async getImage(name: string) {
    const imageRef = ref(imagesRef, name);
    return await getDownloadURL(imageRef);
  }
}

export default new DataService();
