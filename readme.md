# Media Management Service

## Description
This service stores

### Prerequisites
* Java 8
* Maven 3.6.1+

## Build and Run

### Local
Use spring profile "local" to be able to start the app locally.

```
-Dspring.profiles.active=local
```

#### Lombok

Project Lombok is a java library that automatically plugs into your editor and build tools. Never write another getter or equals method again, with one annotation your class has a fully featured builder, automate your logging variables, and much more.

##### Plugin Installation
- Using IDE built-in plugin system on Windows:
  - <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "lombok"</kbd> > <kbd>Install Plugin</kbd>
- Using IDE built-in plugin system on MacOs:
  - <kbd>Preferences</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "lombok"</kbd> > <kbd>Install Plugin</kbd>

Restart IDE.

##### Required IntelliJ Configuration
In your project: Click <kbd>Preferences</kbd> -> <kbd>Build, Execution, Deployment</kbd> -> <kbd>Compiler, Annotation Processors</kbd>. Click <kbd>Enable Annotation Processing</kbd>

Afterwards you might need to do a complete rebuild of your project via <kbd>Build</kbd> -> <kbd>Rebuild Project</kbd>.

## Testing


© 2019 intelligentworks