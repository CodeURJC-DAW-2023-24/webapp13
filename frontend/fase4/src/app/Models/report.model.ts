export class Report {
  private _id: number | undefined;
  private _owner: number;
  private _title: string;
  private _description: string;
  private _userReported: number;
  private _creationDate: string;

  constructor(
    owner: number,
    title: string,
    description: string,
    userReported: number,
    creationDate: string,
    id?: number
  ) {
    this._owner = owner;
    this._title = title;
    this._description = description;
    this._userReported = userReported;
    this._creationDate = creationDate;
    this._id = id;
  }

  // Getters
  get id(): number | undefined {
    return this._id;
  }

  get owner(): number {
    return this._owner;
  }

  get title(): string {
    return this._title;
  }

  get description(): string {
    return this._description;
  }

  get userReported(): number {
    return this._userReported;
  }

  get creationDate(): string {
    return this._creationDate;
  }

  // Setters
  set id(value: number) {
    this._id = value;
  }

  set owner(value: number) {
    this._owner = value;
  }

  set title(value: string) {
    this._title = value;
  }

  set description(value: string) {
    this._description = value;
  }

  set userReported(value: number) {
    this._userReported = value;
  }

  set creationDate(value: string) {
    this._creationDate = value;
  }
}
