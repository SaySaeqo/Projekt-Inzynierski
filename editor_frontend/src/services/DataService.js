import firebase from "../firebase";

const db = firebase.collection("/exhibits");

class DataService {
  getAll() {
    return db;
  }

  create(exhibit) {
    return db.add(exhibit);
  }

  update(id, value) {
    return db.doc(id).update(value);
  }

  delete(id) {
    return db.doc(id).delete();
  }
}

export default new DataService();
