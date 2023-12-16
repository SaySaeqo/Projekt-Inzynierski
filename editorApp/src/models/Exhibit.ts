import firebase from "firebase/compat";
import DocumentData = firebase.firestore.DocumentData;

export class Exhibit {
  id: string; // id of exhibit
  name: string; // name of exhibit
  description: string; //short description
  extra: ExhibitPair[]; //extra parameters
  widgets: Widget[];
  icon: string; // path to image with icon
  location: string; // path to image with location on exhibition

  constructor();
  constructor(id: string, name: string);
  constructor(id?: string, name?: string) {
    this.id = id || "";
    this.name = name || "";
    this.description = "";
    this.extra = [];
    this.widgets = [];
    this.icon = "";
    this.location = "";
  }

  toFirestoreConverter() {
    // const pairArr: ExhibitPair[] = [];
    // Array.from(this.extra.entries()).forEach((el) => {
    //   pairArr.push(new ExhibitPair(el[0], el[1]));
    // });

    return {
      name: this.name,
      description: this.description,
      extra: JSON.stringify(this.extra),
      widgets: JSON.stringify(this.widgets),
      icon: this.icon,
      location: this.location,
    };
  }

  static fromFirebaseConverter(id: string, data: DocumentData | undefined) {
    const newExhibit = new Exhibit();
    newExhibit.id = id;
    newExhibit.name = data?.name;
    newExhibit.description = data?.description;
    newExhibit.widgets = (JSON.parse(data?.widgets) || []).map(
      (widget: any) =>
        new Widget(
          widget.id,
          widget.type,
          widget.title,
          widget.data,
          widget.imagesURLs
        )
    );
    newExhibit.extra = (JSON.parse(data?.extra) || []).map(
      (extr: any) => new ExhibitPair(extr.value, extr.key, extr.linkId)
    );
    newExhibit.icon = data?.icon;
    newExhibit.location = data?.location;

    // const pairArr: ExhibitPair[] = JSON.parse(data?.extra);
    // pairArr.forEach((el) => {
    //   newExhibit.extra.set(el.value, el.key);
    // });

    return newExhibit;
  }
}

export class Widget {
  id: number;
  type: string;
  title: string;
  data: string;
  imagesURLs: string[];

  constructor(id: number, type: string);
  constructor(
    id: number,
    type: string,
    title: string,
    data: string,
    imagesURLs: string[]
  );
  constructor(
    id: number,
    type: string,
    title?: string,
    data?: string,
    imagesURLs?: string[]
  ) {
    this.id = id;
    this.type = type;
    this.title = title || "";
    this.data = data || "";
    this.imagesURLs = imagesURLs || [];
  }

  addImageURL(name: string) {
    this.imagesURLs.push(name);
  }
  removeImageURL(name: string) {
    this.imagesURLs.splice(this.imagesURLs.indexOf(name), 1);
  }
}

export class ExhibitPair {
  value: string;
  key: string;
  linkId: string;

  constructor();
  constructor(value: string, key: string);
  constructor(value: string, key: string, linkId: string);
  constructor(value?: string, key?: string, linkId?: string) {
    this.value = value || "";
    this.key = key || "";
    this.linkId = linkId || "";
  }
}
