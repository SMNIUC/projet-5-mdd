import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PostDetail } from '../../../core/models/post.model';
import { PostService } from '../../../core/services/post.service';

@Component({
  selector: 'app-read-article',
  templateUrl: './read-article.component.html',
  styleUrls: ['./read-article.component.scss'],
})
export class ReadArticleComponent implements OnInit {
  post: PostDetail | null = null;
  isLoading = true;
  hasError = false;
  commentForm: FormGroup;
  isSubmitting = false;
  private postId!: number;

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private fb: FormBuilder
  ) {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.minLength(1)]],
    });
  }

  ngOnInit(): void {
    this.postId = Number(this.route.snapshot.paramMap.get('id'));
    this.postService.getById(this.postId).subscribe({
      next: (post) => {
        this.post = post;
        this.isLoading = false;
      },
      error: () => {
        this.hasError = true;
        this.isLoading = false;
      },
    });
  }

  submitComment(): void {
    if (this.commentForm.invalid || this.isSubmitting || !this.post) return;
    this.isSubmitting = true;
    this.postService.addComment(this.postId, this.commentForm.value).subscribe({
      next: (comment) => {
        this.post!.comments.push(comment);
        this.commentForm.reset();
        this.isSubmitting = false;
      },
      error: () => {
        this.isSubmitting = false;
      },
    });
  }
}
