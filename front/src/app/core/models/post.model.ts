export interface Post {
  id: number;
  title: string;
  content: string;
  authorUsername: string;
  topicName: string;
  createdAt: string;
}

export interface Comment {
  id: number;
  content: string;
  authorUsername: string;
  createdAt: string;
}

export interface PostDetail extends Post {
  comments: Comment[];
}

export interface CreatePostRequest {
  title: string;
  content: string;
  topicId: number;
}

export interface CreateCommentRequest {
  content: string;
}
