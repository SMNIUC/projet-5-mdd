export interface RegisterRequest {
  email: string;
  username: string;
  password: string;
}

export interface LoginRequest {
  identifier: string; // email or username
  password: string;
}

export interface AuthResponse {
  message: string;
}
