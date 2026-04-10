export interface Post {
  id: number;
  title: string;
  content: string;
  authorUsername: string;
  topicName: string;
  createdAt: string;
}

export interface CreatePostRequest {
  title: string;
  content: string;
  topicId: number;
}
