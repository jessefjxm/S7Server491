package com.bdoemu.gameserver.model.misc.enums;

public enum EDialogButtonType {
    Normal(0),
    Knowledge(1),
    Function(2),
    CutScene(3),
    Exchange(4),
    ExceptExchange(5),
    Unknown6(6),
    Unknown(99);

    private byte id;

    EDialogButtonType(final int id) {
        this.id = (byte) id;
    }

    public static EDialogButtonType valueOf(final int id) {
        for (final EDialogButtonType buttonType : values()) {
            if (buttonType.id == id) {
                return buttonType;
            }
        }
        throw new IllegalArgumentException("Invalid EDialogButtonType id: " + id);
    }
}