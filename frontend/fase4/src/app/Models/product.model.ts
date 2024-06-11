export class Product {
  private _id: number;
  private _title: string;
  private _address: string;
  private _price: number;
  private _description: string;
  private _imageFile?: Blob;
  private _image: boolean;
  private _owner: number;
  private _productType: number;
  private _imageUrl: string;

  constructor(
    title: string,
    address: string,
    price: number,
    description: string,
    image: boolean,
    owner: number,
    productType: number,
    imageUrl: string,
    id: number,
    imageFile?: Blob
  ) {
    this._id = id;
    this._title = title;
    this._address = address;
    this._price = price;
    this._description = description;
    this._imageFile = imageFile;
    this._image = image;
    this._owner = owner;
    this._productType = productType;
    this._imageUrl = imageUrl;
  }

  // Getters
  get id(): number {
    return this._id;
  }

  get title(): string {
    return this._title;
  }

  get address(): string {
    return this._address;
  }

  get price(): number {
    return this._price;
  }

  get description(): string {
    return this._description;
  }

  get imageFile(): Blob | undefined {
    return this._imageFile;
  }

  get image(): boolean {
    return this._image;
  }

  get owner(): number {
    return this._owner;
  }

  get productType(): number {
    return this._productType;
  }

  get imageUrl(): string {
    return this._imageUrl;
  }

  // Setters
  set id(value: number) {
    this._id = value;
  }

  set title(value: string) {
    this._title = value;
  }

  set address(value: string) {
    this._address = value;
  }

  set price(value: number) {
    this._price = value;
  }

  set description(value: string) {
    this._description = value;
  }

  set imageFile(value: Blob | undefined) {
    this._imageFile = value;
  }

  set image(value: boolean) {
    this._image = value;
  }

  set owner(value: number) {
    this._owner = value;
  }

  set productType(value: number) {
    this._productType = value;
  }

  set imageUrl(value: string) {
    this._imageUrl = value;
  }
}
