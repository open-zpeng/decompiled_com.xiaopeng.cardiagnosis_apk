package com.nineoldandroids.util;
/* loaded from: classes4.dex */
public abstract class IntProperty<T> extends Property<T, Integer> {
    public abstract void setValue(T t, int i);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.nineoldandroids.util.Property
    public /* bridge */ /* synthetic */ void set(Object x0, Integer num) {
        set2((IntProperty<T>) x0, num);
    }

    public IntProperty(String name) {
        super(Integer.class, name);
    }

    /* renamed from: set  reason: avoid collision after fix types in other method */
    public final void set2(T object, Integer value) {
        set2((IntProperty<T>) object, Integer.valueOf(value.intValue()));
    }
}
