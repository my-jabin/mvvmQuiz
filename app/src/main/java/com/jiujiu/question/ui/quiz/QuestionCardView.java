package com.jiujiu.question.ui.quiz;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.jiujiu.question.R;
import com.jiujiu.question.data.model.QuestionEntity;
import com.jiujiu.question.data.model.Type;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeHead;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NonReusable
@Layout(R.layout.question_cardview)
public class QuestionCardView {

    public interface QuestionCardClickListener {
        void onRightAnswerClick(QuestionEntity entity);

        void onWrongAnswerClick(QuestionEntity entity);

        void lockView();

        void unLockView();
    }

    private static final String TAG = "QuestionCardView";
    private final Context mContext;

    private QuestionEntity mQuestionEntity;
    private int mNumber;
    private Type mCurrentType;

    private Button correctAnswerButtion;

    private boolean mHasAnswered = false;

    private QuestionCardClickListener mListener;

    @View(R.id.tv_question)
    TextView questionText;

    @View(R.id.tv_question_title)
    TextView questionTitle;

    @View(R.id.tv_category)
    TextView categoryText;

    @View(R.id.tv_difficulty)
    TextView difficultyText;

    @View(R.id.layout_multiple_answer)
    LinearLayout multipleAnswerLayout;

    @View(R.id.layout_boolean_answer)
    LinearLayout booleanAnswerLayout;

    @View(R.id.button_option_true)
    MaterialButton trueButton;

    @View(R.id.button_option_false)
    MaterialButton falseButton;

    @View(R.id.button_optionA)
    MaterialButton optionA;

    @View(R.id.button_optionB)
    MaterialButton optionB;

    @View(R.id.button_optionC)
    MaterialButton optionC;

    @View(R.id.button_optionD)
    MaterialButton optionD;

    public QuestionCardView(Context context, QuestionCardClickListener listener, QuestionEntity questionEntity, int number) {
        mQuestionEntity = questionEntity;
        mNumber = number;
        this.mListener = listener;
        this.mContext = context;
    }

    @Resolve
    public void onResolve() {
        questionTitle.setText(String.valueOf(mNumber));
        String category = mQuestionEntity.category.replace("Entertainment:","").trim();
        categoryText.setText(category);
        difficultyText.setText(mQuestionEntity.difficulty.toString());
        questionText.setText(mQuestionEntity.question);
        if (mQuestionEntity.type == Type.MultipleChoice) {
            mCurrentType = Type.MultipleChoice;
            multipleAnswerLayout.setVisibility(android.view.View.VISIBLE);
        } else {
            mCurrentType = Type.TrueFalse;
            booleanAnswerLayout.setVisibility(android.view.View.VISIBLE);
        }
        setupAnswerButtons();
    }


    private void setupAnswerButtons() {
        correctAnswerButtion = pickCorrectionAnswerButton();
        if (mCurrentType == Type.MultipleChoice) {
            correctAnswerButtion.setText(mQuestionEntity.correctAnswer);
            List<MaterialButton> inCorrectButtons = getInCorrectionButtons();
            for (int i = 0; i < inCorrectButtons.size(); i++) {
                inCorrectButtons.get(i).setText(mQuestionEntity.inCorrectAnswer.get(i));
            }
        }
    }

    private List<MaterialButton> getInCorrectionButtons() {
        List<MaterialButton> list = new ArrayList<>();
        if (optionA != correctAnswerButtion) {
            list.add(optionA);
        }
        if (optionB != correctAnswerButtion) {
            list.add(optionB);
        }
        if (optionC != correctAnswerButtion) {
            list.add(optionC);
        }
        if (optionD != correctAnswerButtion) {
            list.add(optionD);
        }
        return list;
    }

    private MaterialButton pickCorrectionAnswerButton() {
        if (mCurrentType == Type.MultipleChoice) {
            Random r = new Random();
            int correctionPositon = r.nextInt(4);
            switch (correctionPositon) {
                case 0:
                    return optionA;
                case 1:
                    return optionB;
                case 2:
                    return optionC;
                case 3:
                    return optionD;
            }
        } else {
            return Boolean.valueOf(mQuestionEntity.correctAnswer) ? trueButton : falseButton;
        }
        return null;
    }


    @Click(R.id.button_option_true)
    public void onTrueButtonClick() {
        showCorrectAnswer(trueButton);
    }

    @Click(R.id.button_option_false)
    public void onFalseButtonClick() {
        showCorrectAnswer(falseButton);
    }

    @Click(R.id.button_optionA)
    public void onOptionAClick() {
        showCorrectAnswer(optionA);
    }

    @Click(R.id.button_optionB)
    public void onOptionBClick() {
        showCorrectAnswer(optionB);
    }

    @Click(R.id.button_optionC)
    public void onOptionCClick() {
        showCorrectAnswer(optionC);
    }

    @Click(R.id.button_optionD)
    public void onOptionDClick() {
        showCorrectAnswer(optionD);
    }

    private void showCorrectAnswer(MaterialButton clicker) {
        if (!mHasAnswered) {
            mListener.unLockView();
            mHasAnswered = true;
            if (clicker == correctAnswerButtion) {
                mListener.onRightAnswerClick(this.mQuestionEntity);
            } else {
                mListener.onWrongAnswerClick(this.mQuestionEntity);
            }
            clicker.setBackgroundTintList(mContext.getColorStateList(R.color.option_wrong_answer));
            correctAnswerButtion.setBackgroundTintList(mContext.getColorStateList(R.color.option_right_answer));
        }
    }

    @SwipeHead
    public void onSwipeHead() {
        Log.d(TAG, "onSwipeHead");
        mListener.lockView();
    }

//    @SwipeOut
//    public void onSwipedOut() {
//        Log.d(TAG, "onSwipedOut: ");
//    }

//    @SwipeCancelState
//    public void onSwipeCancelState() {
//        Log.d(TAG, "onSwipeCancelState: ");
//    }
//
//    @SwipeIn
//    public void onSwipeIn() {
//        Log.d(TAG, "onSwipeIn: ");
//    }

//    @SwipeInState
//    public void onSwipeInState() {
//        Log.d(TAG, "onSwipeInState: ");
//    }

//    @SwipeOutState
//    public void onSwipeOutState() {
//        Log.d(TAG, "onSwipeOutState: ");
//    }

}
