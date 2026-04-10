import { Component, OnInit } from '@angular/core';
import { Topic } from '../../core/models/topic.model';
import { TopicService } from '../../core/services/topic.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  topics: Topic[] = [];
  isLoading = true;

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    this.topicService.getAll().subscribe({
      next: (topics) => {
        this.topics = topics;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  onSubscribe(topic: Topic): void {
    this.topicService.subscribe(topic.id).subscribe({
      next: () => {
        topic.subscribed = true;
      },
    });
  }
}
