
export interface User {
  userID: number;
  encodedPassword: string;
  fullName: string;
  username: string;
  userEmail: string;
  income: number;
  expense: number;
  userImg: Blob;
  roles: string[];
  reviewList: number[];
}
