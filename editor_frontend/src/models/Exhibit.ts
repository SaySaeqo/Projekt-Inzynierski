import firebase from "firebase/compat";
import DocumentData = firebase.firestore.DocumentData;
import exhibitEditor from "@/components/ExhibitEditor.vue";

export class Exhibit {
  id: string; // id of exhibit
  name: string; // name of exhibit
  description: string; //short description
  extra: Map<string, string>; //extra parameters
  widgets: Widget[];
  icon: string; // path to image with icon
  location: string; // path to image with location on exhibition

  constructor();
  constructor(id: string, name: string);
  constructor(id?: string, name?: string) {
    this.id = id || "";
    this.name = name || "";
    this.description = "";
    this.extra = new Map<string, string>();
    this.widgets = [];
    this.icon = "";
    this.location = "";
  }

  toFirestoreConverter() {
    const pairArr: ExhibitPair[] = [];
    Array.from(this.extra.entries()).forEach((el) => {
      pairArr.push(new ExhibitPair(el[0], el[1]));
    });

    return {
      name: this.name,
      description: this.description,
      extra: JSON.stringify(pairArr),
      widgets: JSON.stringify(this.widgets),
    };
  }

  static fromFirebaseConverter(id: string, data: DocumentData | undefined) {
    const newExhibit = new Exhibit();
    newExhibit.id = id;
    newExhibit.name = data?.name;
    newExhibit.description = data?.description;
    newExhibit.widgets = JSON.parse(data?.widgets);

    const pairArr: ExhibitPair[] = JSON.parse(data?.extra);
    pairArr.forEach((el) => {
      newExhibit.extra.set(el.value, el.key);
    });

    return newExhibit;
  }
}

export class Widget {
  id: number;
  type: string;
  data: string;

  constructor() {
    this.id = 0;
    this.type = "";
    this.data = "";
  }
}

class ExhibitPair {
  value: string;
  key: string;

  constructor(value: string, key: string) {
    this.value = value;
    this.key = key;
  }
}
