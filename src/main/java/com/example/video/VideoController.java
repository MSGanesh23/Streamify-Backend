package com.example.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
  // Allow frontend requests from React app running on localhost:5173
public class VideoController {

    @Autowired
    private VideoRepository videoRepository;

    // Get all videos
    @GetMapping
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    // Get a video by ID
    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        Optional<Video> video = videoRepository.findById(id);
        return video.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    

    // Update an existing video
    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long id, @Valid @RequestBody Video updatedVideo) {
        // Check if video exists
        Optional<Video> existingVideoOpt = videoRepository.findById(id);
        if (!existingVideoOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Video existingVideo = existingVideoOpt.get();
        // Update the fields of the existing video
        existingVideo.setTitle(updatedVideo.getTitle());
        existingVideo.setDescription(updatedVideo.getDescription());
        existingVideo.setGenres(updatedVideo.getGenres());
        existingVideo.setYear(updatedVideo.getYear());
        existingVideo.setDuration(updatedVideo.getDuration());
        existingVideo.setThumbnailUrl(updatedVideo.getThumbnailUrl());
        existingVideo.setDriveFileId(updatedVideo.getDriveFileId());

        Video savedVideo = videoRepository.save(existingVideo);
        return ResponseEntity.ok(savedVideo);  // Returning 200 OK with updated video
    }

    // Delete a video by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        Optional<Video> videoOpt = videoRepository.findById(id);
        if (!videoOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        videoRepository.deleteById(id);
        return ResponseEntity.noContent().build();  // Returning 204 No Content after successful delete
    }
    @PostMapping
    public ResponseEntity<Video> addVideo(@Valid @RequestBody Video video) {
        // Validation for mandatory fields
        if (video.getTitle() == null || video.getDriveFileId() == null) {
            return ResponseEntity.badRequest().body(null);  // Returning 400 Bad Request
        }

        Video savedVideo = videoRepository.save(video);
        return ResponseEntity.status(201).body(savedVideo);  // Returning 201 Created status
    }
}
