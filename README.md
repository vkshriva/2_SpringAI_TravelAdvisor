# Travel Planner API

## Overview

This API provides travel planning services, including destination recommendations, itinerary generation, and integration with external services like weather forecasts.

## Weather API & Function Calling

### Why this is needed

OpenAI (and other chat models) do not have real-time access to current weather or live forecast data. To provide accurate current-weather or forecast information alongside the travel plan, the application needs to call a live Weather API and surface those results to the user/model.

### High-level flow (function-calling style)

1. User requests a travel plan and also asks for current weather or forecast for the destination (for example by adding `includeWeather=true` or calling a dedicated endpoint).
2. The application sends the travel prompt to the chat model as usual. If the model needs live weather data, we follow a function-calling pattern:
   - Provide the model with a small function schema describing a `getWeather(city, type)` function (where `type` is `current` or `forecast`).
   - If the model 'calls' that function (i.e., it returns an instruction to call it), the application executes the corresponding Weather API request to the external provider.
   - The application returns the API result back to the model as the function response and ask the model to continue/format the final travel plan including the weather data.

### Suggested API endpoints

- Add a dedicated endpoint that explicitly requests weather-enabled plans:
  - POST /api/recommend-with-weather?city=Paris&days=3&includeWeather=true

### Configuration (application.properties)

Add properties for your chosen Weather provider (example uses OpenWeatherMap):

```
weather.api.provider=openweathermap
weather.api.key=${OPENWEATHERMAP_API_KEY}
weather.api.baseUrl=https://api.openweathermap.org/data/2.5
```

### Example Weather API calls

- Current weather (OpenWeatherMap):
  - GET {weather.api.baseUrl}/weather?q={city}&appid={weather.api.key}&units=metric
- Forecast (5-day / 3-hour):
  - GET {weather.api.baseUrl}/forecast?q={city}&appid={weather.api.key}&units=metric

### Implementation notes

- Function-calling support depends on the chat model/provider and the `spring-ai` client you are using. If the provider supports function calling natively, follow its function-schema/response pattern. If not, implement the pattern in your application:
  - Define a simple contract: when the model's text output contains a structured request for `getWeather`, parse it and call the external Weather API.
  - Send the API response back to the model as context and ask the model to produce the final JSON travel plan.

- Security: store API keys in environment variables and reference them from `application.properties` (avoid committing keys to source control).

- Example quick flow (pseudo):
  - Controller receives request with `includeWeather=true`.
  - Send prompt to model with a short function schema describing `getWeather(city,type)`.
  - If model indicates a function call, the controller calls the weather API and returns the data into the chat conversation as the function result.
  - Ask the model to produce the final TravelResponse JSON including weather/forecast info.

### Notes on UI/consumer contract

- The TravelResponse DTO can be extended to include a `weather` or `forecast` field. Keep the weather data small and structured (temperature, condition, lastUpdated) so the consumer can display it easily.

Example small weather snippet to return inside the TravelResponse JSON:

```
"weather": {
  "type": "current",
  "temperatureC": 12.3,
  "condition": "Light rain",
  "observationTime": "2025-11-09T08:30:00Z"
}
```

### Troubleshooting and tips

- If your chat model does not support structured function calling, implement a deterministic convention (e.g., the model outputs a JSON block with `{ "function": "getWeather", "city": "X", "type": "current" }`) that your application recognizes and executes.
- Keep the model's instructions short and deterministic when you need structured outputs; include an explicit system instruction stating that final output must be a single JSON object matching `TravelResponse`.
