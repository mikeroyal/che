/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.ide.ui.dialogs.choice;

import static com.google.gwt.safehtml.shared.SimpleHtmlSanitizer.sanitizeHtml;

import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import javax.validation.constraints.NotNull;
import org.eclipse.che.commons.annotation.Nullable;
import org.eclipse.che.ide.ui.dialogs.confirm.ConfirmCallback;

/**
 * {@link ChoiceDialog} implementation.
 *
 * @author Mickaël Leduque
 * @author Artem Zatsarynnyi
 */
public class ChoiceDialogPresenter implements ChoiceDialog, ChoiceDialogView.ActionDelegate {

  /** This component view. */
  private final ChoiceDialogView view;

  /** The callback used on first button. */
  private final ConfirmCallback firstChoiceCallback;

  /** The callback used on second button. */
  private final ConfirmCallback secondChoiceCallback;

  /** The callback used on third button. */
  private final ConfirmCallback thirdChoiceCallback;

  @AssistedInject
  public ChoiceDialogPresenter(
      final @NotNull ChoiceDialogView view,
      final @NotNull @Assisted("title") String title,
      final @NotNull @Assisted("message") String message,
      final @NotNull @Assisted("firstChoice") String firstChoiceLabel,
      final @NotNull @Assisted("secondChoice") String secondChoiceLabel,
      final @Nullable @Assisted("firstCallback") ConfirmCallback firstChoiceCallback,
      final @Nullable @Assisted("secondCallback") ConfirmCallback secondChoiceCallback) {
    this(
        view,
        title,
        new InlineHTML(sanitizeHtml(message)),
        firstChoiceLabel,
        secondChoiceLabel,
        "",
        firstChoiceCallback,
        secondChoiceCallback,
        null);
  }

  @AssistedInject
  public ChoiceDialogPresenter(
      final @NotNull ChoiceDialogView view,
      final @NotNull @Assisted String title,
      final @NotNull @Assisted IsWidget content,
      final @NotNull @Assisted("firstChoice") String firstChoiceLabel,
      final @NotNull @Assisted("secondChoice") String secondChoiceLabel,
      final @Nullable @Assisted("firstCallback") ConfirmCallback firstChoiceCallback,
      final @Nullable @Assisted("secondCallback") ConfirmCallback secondChoiceCallback) {
    this(
        view,
        title,
        content,
        firstChoiceLabel,
        secondChoiceLabel,
        "",
        firstChoiceCallback,
        secondChoiceCallback,
        null);
  }

  @AssistedInject
  public ChoiceDialogPresenter(
      final @NotNull ChoiceDialogView view,
      final @NotNull @Assisted("title") String title,
      final @NotNull @Assisted("message") String message,
      final @NotNull @Assisted("firstChoice") String firstChoiceLabel,
      final @NotNull @Assisted("secondChoice") String secondChoiceLabel,
      final @NotNull @Assisted("thirdChoice") String thirdChoiceLabel,
      final @Nullable @Assisted("firstCallback") ConfirmCallback firstChoiceCallback,
      final @Nullable @Assisted("secondCallback") ConfirmCallback secondChoiceCallback,
      final @Nullable @Assisted("thirdCallback") ConfirmCallback thirdChoiceCallback) {
    this(
        view,
        title,
        new InlineHTML(sanitizeHtml(message)),
        firstChoiceLabel,
        secondChoiceLabel,
        thirdChoiceLabel,
        firstChoiceCallback,
        secondChoiceCallback,
        thirdChoiceCallback);
  }

  @AssistedInject
  public ChoiceDialogPresenter(
      final @NotNull ChoiceDialogView view,
      final @NotNull @Assisted String title,
      final @NotNull @Assisted IsWidget content,
      final @NotNull @Assisted("firstChoice") String firstChoiceLabel,
      final @NotNull @Assisted("secondChoice") String secondChoiceLabel,
      final @NotNull @Assisted("thirdChoice") String thirdChoiceLabel,
      final @Nullable @Assisted("firstCallback") ConfirmCallback firstChoiceCallback,
      final @Nullable @Assisted("secondCallback") ConfirmCallback secondChoiceCallback,
      final @Nullable @Assisted("thirdCallback") ConfirmCallback thirdChoiceCallback) {
    this.view = view;
    this.view.setContent(content);
    this.view.setTitleCaption(title);
    this.view.setFirstChoiceLabel(firstChoiceLabel);
    this.view.setSecondChoiceLabel(secondChoiceLabel);
    this.view.setThirdChoiceLabel(thirdChoiceLabel);
    this.firstChoiceCallback = firstChoiceCallback;
    this.secondChoiceCallback = secondChoiceCallback;
    this.thirdChoiceCallback = thirdChoiceCallback;
    this.view.setDelegate(this);
  }

  @Override
  public void firstChoiceClicked() {
    this.view.closeDialog();
    if (this.firstChoiceCallback != null) {
      this.firstChoiceCallback.accepted();
    }
  }

  @Override
  public void secondChoiceClicked() {
    this.view.closeDialog();
    if (this.secondChoiceCallback != null) {
      this.secondChoiceCallback.accepted();
    }
  }

  @Override
  public void thirdChoiceClicked() {
    this.view.closeDialog();
    if (this.thirdChoiceCallback != null) {
      this.thirdChoiceCallback.accepted();
    }
  }

  @Override
  public void onEnterClicked() {
    if (view.isFirstButtonInFocus()) {
      firstChoiceClicked();
      return;
    }

    if (view.isSecondButtonInFocus()) {
      secondChoiceClicked();
      return;
    }

    if (view.isThirdButtonInFocus()) {
      thirdChoiceClicked();
    }
  }

  @Override
  public void show() {
    this.view.showDialog();
  }
}
