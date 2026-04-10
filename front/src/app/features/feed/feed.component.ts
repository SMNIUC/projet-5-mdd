import { Component, OnInit } from '@angular/core';
import { Post } from '../../core/models/post.model';
import { PostService } from '../../core/services/post.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss'],
})
export class FeedComponent implements OnInit {
  posts: Post[] = [];
  isLoading = true;
  order: 'asc' | 'desc' = 'desc';

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.loadFeed();
  }

  toggleOrder(): void {
    this.order = this.order === 'desc' ? 'asc' : 'desc';
    this.loadFeed();
  }

  private loadFeed(): void {
    this.isLoading = true;
    this.postService.getFeed(this.order).subscribe({
      next: (posts) => {
        this.posts = posts;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }
}
