package com.morgage.model.data;

import com.morgage.model.Pawnee;
import com.morgage.model.PawneeInfo;

import java.io.Serializable;

public class ExistPawneeData implements Serializable {
    private Pawnee pawnee;
    private PawneeInfo pawneeInfo;

    public ExistPawneeData() {
    }

    public Pawnee getPawnee() {
        return pawnee;
    }

    public void setPawnee(Pawnee pawnee) {
        this.pawnee = pawnee;
    }

    public PawneeInfo getPawneeInfo() {
        return pawneeInfo;
    }

    public void setPawneeInfo(PawneeInfo pawneeInfo) {
        this.pawneeInfo = pawneeInfo;
    }
}
