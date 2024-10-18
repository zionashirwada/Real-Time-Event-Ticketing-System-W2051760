import { Configuration } from './../../types/configuration.type';
import { Component, OnInit } from '@angular/core';
import { ConfigurationService } from '../../services/configuration.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  standalone: true,
  styleUrls: ['./configuration-form.component.css'],
  imports: [CommonModule, FormsModule],
})
export class ConfigurationFormComponent implements OnInit {
  configuration: Configuration = new Configuration();

  constructor(private configService: ConfigurationService) {}

  ngOnInit(): void {
    this.loadConfiguration();
  }

  loadConfiguration(): void {
    this.configService.getConfiguration().subscribe(
      (config) => {
        this.configuration = config;
      },
      (error) => {
        console.error('Error loading configuration:', error);
        //set default values
        this.configuration = new Configuration();
      }
    );
  }

  onSubmit(): void {
    this.configService.saveConfiguration(this.configuration).subscribe(
      (response) => {
        console.log(response);
        alert('Configuration saved successfully');
      },
      (error) => {
        console.error('Error saving configuration:', error);
        alert('Error saving configuration');
      }
    );
  }
}
