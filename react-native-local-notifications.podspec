require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-local-notifications"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-local-notifications
                   DESC
  s.homepage     = "https://github.com/spezzino/react-native-local-notifications"
  # brief license entry:
  s.license      = "MIT"
  # optional - use expanded license entry instead:
  # s.license    = { :type => "MIT", :file => "LICENSE" }
  s.authors      = { "Stefano Pezzino" => "contact@paraguaydev.xyz" }
  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/spezzino/react-native-local-notifications.git", :tag => "#{s.version}" }

  s.source_files = "ios/**/*.{h,c,m,swift}"
  s.requires_arc = true

  s.dependency "React"
  # ...
  # s.dependency "..."
end

