import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { Topic } from '../../core/models/topic.model';
import { UserProfile } from '../../core/models/user.model';
import { TopicService } from '../../core/services/topic.service';
import { UserService } from '../../core/services/user.service';

const PASSWORD_PATTERN =
  /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+\[\]{};:,.<>?]).{8,}$/;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  user: UserProfile | null = null;
  subscriptions: Topic[] = [];
  isLoading = true;
  editForm!: FormGroup;
  isSaving = false;
  saveSuccess = false;
  serverErrors: Record<string, string> = {};

  constructor(
    private userService: UserService,
    private topicService: TopicService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    forkJoin({
      user: this.userService.getMe(),
      topics: this.topicService.getAll(),
    }).subscribe({
      next: ({ user, topics }) => {
        this.user = user;
        this.subscriptions = topics.filter((t) => t.subscribed);
        this.isLoading = false;
        this.buildForm(user);
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  private buildForm(user: UserProfile): void {
    this.editForm = this.fb.group({
      email: [user.email, [Validators.required, Validators.email]],
      username: [user.username, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
      password: ['', [Validators.pattern(PASSWORD_PATTERN)]],
    });
  }

  get email() { return this.editForm.get('email')!; }
  get username() { return this.editForm.get('username')!; }
  get password() { return this.editForm.get('password')!; }

  onSave(): void {
    if (this.editForm.invalid || this.isSaving) return;
    this.isSaving = true;
    this.saveSuccess = false;
    this.serverErrors = {};

    const { email, username, password } = this.editForm.value;
    const request: Record<string, string> = { email, username };
    if (password) request['password'] = password;

    this.userService.updateMe(request).subscribe({
      next: (updated) => {
        this.user = updated;
        this.editForm.patchValue({ email: updated.email, username: updated.username, password: '' });
        this.isSaving = false;
        this.saveSuccess = true;
      },
      error: (err) => {
        this.isSaving = false;
        if (err.status === 400 || err.status === 409) {
          this.serverErrors = err.error ?? {};
        }
      },
    });
  }

  onUnsubscribe(topic: Topic): void {
    this.topicService.unsubscribe(topic.id).subscribe({
      next: () => {
        this.subscriptions = this.subscriptions.filter((t) => t.id !== topic.id);
      },
    });
  }
}
