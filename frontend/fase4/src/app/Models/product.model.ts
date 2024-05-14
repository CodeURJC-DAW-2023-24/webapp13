export interface Product {
  id: number;
  title: string;
  address: string;
  price: number;
  description: string;
  imageFile: Blob;
  image: boolean;
  owner: number;
  productType: number;
}
