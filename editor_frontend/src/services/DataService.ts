//import coll from "@/initfirestore";
import { getFirestore } from "firebase/firestore";
import firebaseApp from "@/initfirestore";
import { query, collection, getDocs } from "firebase/firestore"

//let db = getFirestore(firebaseApp);
const db = getFirestore(firebaseApp);

class DataService {
  async getAll() {
    let sth = [{ id: "1", name: "1" }];
    const querySnap = await getDocs(query(collection(db, "exhibits")));
    /*db.collection("exhibits")
      .get()
      .then((query) => {
        query.forEach((doc) => {
          sth.push({
            id: doc.data().id,
            name: doc.data().name,
          });
        });
      });*/

    querySnap.forEach((doc) => {
      sth.push({
        id: doc.data().id,
        name: doc.data().name,
      });
    });

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
