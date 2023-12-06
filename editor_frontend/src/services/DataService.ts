import { getFirestore } from "firebase/firestore";
import firebaseApp from "@/initfirestore";
import { query, collection, getDocs, getDoc, doc } from "firebase/firestore";
import { Exhibit } from "@/models/Exhibit";

const db = getFirestore(firebaseApp);

class DataService {
  async getAll() {
    const sth = [] as Exhibit[];
    sth.pop();
    const querySnap = await getDocs(query(collection(db, "exhibits")));

    // add each doc to 'countries' array
    querySnap.forEach((doc) => {
      sth.push(new Exhibit(doc.id, doc.data().name));
    });

    return sth;
  }

  async getOne(id: string) {
    const sth = new Exhibit();
    const snap = await getDoc(doc(db, "exhibits", id));
    sth.id = id;
    sth.name = snap.data()?.name;
    sth.description = snap.data()?.description;
    return sth;
  }

  /*create(exhibit) {
    return coll.add(exhibit);
  }

  update(id, value) {
    return coll.doc(id).update(value);
  }

  delete(id) {
    return coll.doc(id).delete();
  }*/
}

export default new DataService();
