package study.springstudy.conversion;


import org.springframework.core.convert.converter.Converter;

public class StringToMemberIdConverter implements Converter<String, MemberId> {
    private static final String PREFIX = "member-";

    @Override
    public MemberId convert(String source) {
        if(!source.startsWith(PREFIX)) {
            throw new IllegalArgumentException(
                    "회원 ID는 member-숫자 형식이어야 합니다."
            );
        }
        String numberPart = source.substring(PREFIX.length());

        try {
            Long value = Long.valueOf(numberPart);
            return new MemberId(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "회원 ID의 숫자 부분이 올바르지 않습니다.",
                    exception
            );
        }
    }


}
