export interface UserProfile {
  id: number;
  email: string;
  username: string;
}

export interface UpdateUserRequest {
  email?: string;
  username?: string;
  password?: string;
}
