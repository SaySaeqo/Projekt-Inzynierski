
export class Exhibit {
    id: string; // id of exhibit
    name: string; // name of exhibit
    description: string; //short description
    extra: Map<string, string>; //extra parameters
    widgets: Widget[];

    constructor() {
        this.id = '';
        this.name = '';
        this.description = '';
        this.extra = new Map<string, string>();
        this.widgets = [];
    }
}

export class Widget {
    type: string;
    data: string;

    constructor() {
        this.type = '';
        this.data = '';
    }
}
