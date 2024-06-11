export class User {
  private _userID?: number;
  private _encodedPassword?: string;
  private _fullName?: string;
  private _username?: string;
  private _userEmail?: string;
  private _income: number;
  private _expense: number;
  private _userImg?: Blob;
  private _roles: string[];
  private _reviewList: number[];

  constructor(
    income: number = 0,
    expense: number = 0,
    roles: string[] = [],
    reviewList: number[] = [],
    userID?: number,
    encodedPassword?: string,
    fullName?: string,
    username?: string,
    userEmail?: string,
    userImg?: Blob
  ) {
    this._userID = userID;
    this._encodedPassword = encodedPassword;
    this._fullName = fullName;
    this._username = username;
    this._userEmail = userEmail;
    this._income = income;
    this._expense = expense;
    this._userImg = userImg;
    this._roles = roles;
    this._reviewList = reviewList;
  }

  // Getters
  get userID(): number | undefined {
    return this._userID;
  }

  get encodedPassword(): string | undefined {
    return this._encodedPassword;
  }

  get fullName(): string | undefined {
    return this._fullName;
  }

  get username(): string | undefined {
    return this._username;
  }

  get userEmail(): string | undefined {
    return this._userEmail;
  }

  get income(): number {
    return this._income;
  }

  get expense(): number {
    return this._expense;
  }

  get userImg(): Blob | undefined {
    return this._userImg;
  }

  get roles(): string[] {
    return this._roles;
  }

  get reviewList(): number[] {
    return this._reviewList;
  }

  // Setters
  set userID(value: number | undefined) {
    this._userID = value;
  }

  set encodedPassword(value: string | undefined) {
    this._encodedPassword = value;
  }

  set fullName(value: string | undefined) {
    this._fullName = value;
  }

  set username(value: string | undefined) {
    this._username = value;
  }

  set userEmail(value: string | undefined) {
    this._userEmail = value;
  }

  set income(value: number) {
    this._income = value;
  }

  set expense(value: number) {
    this._expense = value;
  }

  set userImg(value: Blob | undefined) {
    this._userImg = value;
  }

  set roles(value: string[]) {
    this._roles = value;
  }

  set reviewList(value: number[]) {
    this._reviewList = value;
  }
}
