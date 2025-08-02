# YouTube URL Formats for Trailer URL

## Description
The system automatically converts various YouTube URL formats into the correct embed format for displaying the video.

## Supported formats

### 1. Standard format with `v=` parameter
```
https://www.youtube.com/watch?v=dQw4w9WgXcQ
```
**Result:** `https://www.youtube.com/embed/dQw4w9WgXcQ`

### 2. Short format with `youtu.be`
```
https://youtu.be/dQw4w9WgXcQ
```
**Result:** `https://www.youtube.com/embed/dQw4w9WgXcQ`

### 3. Embed format (now correct)
```
https://www.youtube.com/embed/dQw4w9WgXcQ
```
**Result:** `https://www.youtube.com/embed/dQw4w9WgXcQ` (not changing)

### 4. Format with additional parameters
```
https://www.youtube.com/watch?v=dQw4w9WgXcQ&t=30s
https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=PLbpi6ZahtOH6Bl7sRseeJ03Wy9IOY60Yy&index=1
```
**Result:** `https://www.youtube.com/embed/dQw4w9WgXcQ` (parameters removed)

### 5. Timestamp format
```
https://www.youtube.com/watch?v=dQw4w9WgXcQ#t=1m30s
```
**Result:** `https://www.youtube.com/embed/dQw4w9WgXcQ`

## How the conversion works

### Regex Pattern
```java
Pattern.compile(
    "(?:youtube\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})"
)
```

### Logic
1. **Check if URL is already in the correct format**
    - If it starts with `https://www.youtube.com/embed/`, it is not changed

2. **Extract Video ID**
    - Regex is used to find the 11-character video ID
    - All extra parameters are removed

3. **Creating an embed URL**
    - Format: `https://www.youtube.com/embed/{videoId}`

## Usage Examples

### Frontend JavaScript
```javascript
// You can submit a URL in any format
const movieData = {
  name: "Test Movie",
  trailerUrl: "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
};

// The system will automatically convert it
const response = await fetch('/api/movies/add-movie', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify(movieData)
});
```

### cURL examples
```bash
# Old format
curl -X POST http://localhost:8081/api/movies/add-movie \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "name": "Test Movie",
    "trailerUrl": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
    "audio": "Angl.",
    "description": "Test movie",
    "hallNumber": "HALL_2",
    "imageUrl": "https://example.com/image.jpg",
    "movieLength": 120,
    "projectionFormat": "D_2D",
    "subtitles": "Bulg.",
    "movieClass": "C_PLUS",
    "genreCategories": ["ACTION"]
  }'

# Short format
curl -X POST http://localhost:8081/api/movies/add-movie \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "name": "Test Movie 2",
    "trailerUrl": "https://youtu.be/dQw4w9WgXcQ",
    "audio": "Angl.",
    "description": "Test movie 2",
    "hallNumber": "HALL_2",
    "imageUrl": "https://example.com/image.jpg",
    "movieLength": 120,
    "projectionFormat": "D_2D",
    "subtitles": "Bulg.",
    "movieClass": "C_PLUS",
    "genreCategories": ["COMEDY"]
  }'
```

## Validation

### What happens if the URL is not a valid YouTube URL?
- If the regex cannot extract the video ID, the URL is kept as is
- This allows the use of other video platforms (if necessary)

### Check the result
After creating the movie, you can check how the URL was converted:

```bash
GET http://localhost:8081/api/movies/movie/{id}
```

## Notes

1. **Video ID extraction** - The system extracts only the 11-character video ID
2. **Parameters** - All additional parameters are removed
3. **Timestamps** - `&t=30s` or `#t=1m30s` are removed
4. **Playlist parameters** - `&list=...` are removed
5. **Embed parameters** - If the URL is already in embed format, it is not changed

## Testing

Use the test file `src/test/youtube-url-test.http` to check the different formats.