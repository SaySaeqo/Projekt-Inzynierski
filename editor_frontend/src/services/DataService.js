//import coll from "@/initfirestore";
import { getFirestore } from "firebase/firestore";
import firebaseApp from "@/initfirestore";
import { query, collection, getDocs, getDoc, doc } from "firebase/firestore";

//let db = getFirestore(firebaseApp);
const db = getFirestore(firebaseApp);

class DataService {
  async getAll() {
    let sth = [{ id: "1", name: "1" }];
    sth.pop();
    const querySnap = await getDocs(query(collection(db, "exhibits")));

    // add each doc to 'countries' array
    querySnap.forEach((doc) => {
      sth.push({ id: doc.id, name: doc.data().name });
    });

    return sth;
  }

  async getOne(id) {
    const snap = await getDoc(doc(db, "exhibits", id));
    return snap.data();
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
