package teamproject1.letsdoit.common.config.oauth2.company;

import teamproject1.letsdoit.common.config.oauth2.OAuth2UserInfo;
import teamproject1.letsdoit.member.domain.Provider;

import java.util.Map;

public class Google extends OAuth2UserInfo {

    public Google(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getProvider() {
        return Provider.google.toString();
    }
}
