import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Topic } from '../../../core/models/topic.model';
import { PostService } from '../../../core/services/post.service';
import { TopicService } from '../../../core/services/topic.service';

@Component({
  selector: 'app-write-article',
  templateUrl: './write-article.component.html',
  styleUrls: ['./write-article.component.scss'],
})
export class WriteArticleComponent implements OnInit {
  form: FormGroup;
  topics: Topic[] = [];
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private postService: PostService,
    private topicService: TopicService,
    private router: Router
  ) {
    this.form = this.fb.group({
      title:   ['', [Validators.required, Validators.minLength(3)]],
      topicId: [null, Validators.required],
      content: ['', [Validators.required, Validators.minLength(10)]],
    });
  }

  ngOnInit(): void {
    this.topicService.getAll().subscribe({
      next: (topics) => (this.topics = topics),
    });
  }

  get title()   { return this.form.get('title')!; }
  get topicId() { return this.form.get('topicId')!; }
  get content() { return this.form.get('content')!; }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.isLoading = true;
    this.postService.create(this.form.value).subscribe({
      next: () => this.router.navigate(['/feed']),
      error: () => { this.isLoading = false; },
    });
  }
}
