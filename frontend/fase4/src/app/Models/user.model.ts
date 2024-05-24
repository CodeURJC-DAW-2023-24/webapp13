export class User {
  private userID?: number;
  private encodedPassword?: string;
  private fullName?: string;
  private username?: string;
  private userEmail?: string;
  private income: number;
  private expense: number;
  private userImg?: Blob;
  private roles: string[];
  private reviewList: number[];

  constructor();
  constructor(username: string, userImg: Blob, userEmail: string, encodedPassword: string, fullName: string, reviewList: number[], ...roles: string[]);
  constructor(
    username?: string,
    userImg?: Blob,
    userEmail?: string,
    encodedPassword?: string,
    fullName?: string,
    reviewList?: number[],
    ...roles: string[]
  ) {
    this.income = 0;
    this.expense = 0;
    this.roles = roles || [];
    this.reviewList = reviewList || [];
    
    if (username) {
      this.username = username;
      this.userImg = userImg;
      this.userEmail = userEmail;
      this.encodedPassword = encodedPassword;
      this.fullName = fullName;
      this.reviewList = reviewList || [];
      this.roles = roles || [];
    }
  }

  // Getters
  getUserID(): number | undefined {
    return this.userID;
  }

  getEncodedPassword(): string | undefined {
    return this.encodedPassword;
  }

  getFullName(): string | undefined {
    return this.fullName;
  }

  getUsername(): string | undefined {
    return this.username;
  }

  getUserEmail(): string | undefined {
    return this.userEmail;
  }

  getIncome(): number {
    return this.income;
  }

  getExpense(): number {
    return this.expense;
  }

  getUserImg(): Blob | undefined {
    return this.userImg;
  }

  getRoles(): string[] {
    return this.roles;
  }

  getReviewList(): number[] {
    return this.reviewList;
  }

  // Setters
  setUserID(userID: number): void {
    this.userID = userID;
  }

  setEncodedPassword(encodedPassword: string): void {
    this.encodedPassword = encodedPassword;
  }

  setFullName(fullName: string): void {
    this.fullName = fullName;
  }

  setUsername(username: string): void {
    this.username = username;
  }

  setUserEmail(userEmail: string): void {
    this.userEmail = userEmail;
  }

  setIncome(income: number): void {
    this.income = income;
  }

  setExpense(expense: number): void {
    this.expense = expense;
  }

  setUserImg(userImg: Blob): void {
    this.userImg = userImg;
  }

  setRoles(roles: string[]): void {
    this.roles = roles;
  }

  setReviewList(reviewList: number[]): void {
    this.reviewList = reviewList;
  }
}