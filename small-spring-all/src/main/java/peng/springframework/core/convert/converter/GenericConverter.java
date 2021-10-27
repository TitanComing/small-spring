package peng.springframework.core.convert.converter;

import cn.hutool.core.lang.Assert;

import java.util.Set;

/**
 * Create by peng on 2021/10/26.
 * <p>
 * 通用转换器
 */
public interface GenericConverter {

    // 这个转换类能转换的类型
    Set<ConvertiblePair> getConvertibleTypes();

    // 把一个对象从一个类型转换为另外一个类型
    Object convert(Object source, Class sourceType, Class targetType);

    //接口里边定义一个内部类
    //Holder for a source-to-target class pair.
    final class ConvertiblePair {

        private final Class<?> sourceType;

        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            Assert.notNull(sourceType, "Source type must not be null");
            Assert.notNull(targetType, "Target type must not be null");
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return this.sourceType;
        }

        public Class<?> getTargetType() {
            return this.targetType;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != ConvertiblePair.class) {
                return false;
            }
            ConvertiblePair other = (ConvertiblePair) obj;
            return this.sourceType.equals(other.sourceType) && this.targetType.equals(other.targetType);
        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }

}
