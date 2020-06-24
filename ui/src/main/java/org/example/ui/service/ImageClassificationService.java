package org.example.ui.service;

import org.example.ui.model.entity.ImageClassification;
import org.example.ui.model.entity.Status;
import org.example.ui.model.json.ImageClassificationResponse;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ImageClassificationService {

    public List<ImageClassificationResponse> getImageClassifications() {
        return getListWithImageClassifications()
                .stream()
                .map(ImageClassificationResponse::new)
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<ImageClassification> getListWithImageClassifications() {
        return List.of(
                new ImageClassification(
                        "https://scx2.b-cdn.net/gfx/news/hires/2019/2-nature.jpg",
                        "Image #1",
                        LocalDateTime.of(2013, 10, 4, 16, 52, 43),
                        Status.ACTIVE),
                new ImageClassification(
                        "https://s3-us-west-2.amazonaws.com/uw-s3-cdn/wp-content/uploads/sites/6/2017/11/04133712/waterfall.jpg",
                        "Image #2",
                        LocalDateTime.of(2014, 8, 5, 10, 12, 24),
                        Status.ACTIVE),
                new ImageClassification(
                        "https://natgeo.imgix.net/factsheets/thumbnails/01-balance-of-nature.adapt.jpg?auto=compress,format&w=1600&h=900&fit=crop",
                        "Image #3",
                        LocalDateTime.of(2015, 5, 11, 15, 15, 45),
                        Status.ERROR),
                new ImageClassification(
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRxEM1lQyU3UVMBT_FFnbavqyz8981Phc0-Aw&usqp=CAU",
                        "Image #4",
                        LocalDateTime.of(2016, 9, 6, 20, 32, 3),
                        Status.ACTIVE),
                new ImageClassification(
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT_m1rPy1Bj7Kvx4o6b_s_gBTbYW8xnqfMAgg&usqp=CAU",
                        "Image #5",
                        LocalDateTime.of(2017, 8, 12, 17, 32, 57),
                        Status.PENDING),
                new ImageClassification(
                        "https://scx2.b-cdn.net/gfx/news/hires/2019/2-nature.jpg",
                        "Image #6",
                        LocalDateTime.of(2013, 10, 4, 16, 52, 43),
                        Status.ACTIVE),
                new ImageClassification(
                        "https://s3-us-west-2.amazonaws.com/uw-s3-cdn/wp-content/uploads/sites/6/2017/11/04133712/waterfall.jpg",
                        "Image #7",
                        LocalDateTime.of(2014, 8, 5, 10, 12, 24),
                        Status.ACTIVE),
                new ImageClassification(
                        "https://natgeo.imgix.net/factsheets/thumbnails/01-balance-of-nature.adapt.jpg?auto=compress,format&w=1600&h=900&fit=crop",
                        "Image #8",
                        LocalDateTime.of(2015, 5, 11, 15, 15, 45),
                        Status.ERROR),
                new ImageClassification(
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRxEM1lQyU3UVMBT_FFnbavqyz8981Phc0-Aw&usqp=CAU",
                        "Image #9",
                        LocalDateTime.of(2016, 9, 6, 20, 32, 3),
                        Status.ACTIVE),
                new ImageClassification(
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT_m1rPy1Bj7Kvx4o6b_s_gBTbYW8xnqfMAgg&usqp=CAU",
                        "Image #10",
                        LocalDateTime.of(2017, 8, 12, 17, 32, 57),
                        Status.PENDING)
        );
    }

}
