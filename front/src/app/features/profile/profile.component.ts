import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { Topic } from '../../core/models/topic.model';
import { UserProfile } from '../../core/models/user.model';
import { TopicService } from '../../core/services/topic.service';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  user: UserProfile | null = null;
  subscriptions: Topic[] = [];
  isLoading = true;

  constructor(
    private userService: UserService,
    private topicService: TopicService
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
      },
      error: () => {
        this.isLoading = false;
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
