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
import { Exhibit, Widget } from "@/models/Exhibit";
import { v4 as uuidv4 } from "uuid";

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
    const exh = await this.getOne(id);
    exh.widgets.forEach((el) => {
      this.removeAllImages(el);
    });
    await this.removeImage(exh.icon);
    await this.removeImage(exh.location);
    await deleteDoc(doc(db, "exhibits", id));
  }

  async addImage(exhibitId: string, imageFile: File) {
    const uniqueId = uuidv4();
    const extension = imageFile.name.split(".").pop();
    const name = exhibitId + "_" + uniqueId + "." + extension;
    const fileRef = ref(imagesRef, name);
    await uploadBytes(fileRef, imageFile).then(() => {
      console.log("Uploaded a blob or file!");
    });

    return fileRef.name;
  }

  async updateImage(exhibitId: string, imageFile: File, imageName: string) {
    if (imageName != "") {
      await this.removeImage(imageName);
      return this.addImage(exhibitId, imageFile);
    } else {
      return this.addImage(exhibitId, imageFile);
    }
  }

  async removeImage(imageLinkOrName: string) {
    // Create a reference to the file to delete
    const imageName = this.getNameFromLink(imageLinkOrName);
    const imageRef = ref(imagesRef, imageName);
    // Delete the file
    await deleteObject(imageRef).catch((err) => {
      console.log(err);
      return false;
    });

    return true;
  }

  async removeAllImages(widget: Widget) {
    widget.imagesURLs.forEach((image) => {
      this.removeImage(image);
    });
  }

  async getImage(name: string) {
    const imageRef = ref(imagesRef, name);
    return await getDownloadURL(imageRef);
  }

  getNameFromLink(link: string) {
    return link.split("/").pop()?.split("?")[0].split("%2F")?.pop();
  }
}

export default new DataService();
