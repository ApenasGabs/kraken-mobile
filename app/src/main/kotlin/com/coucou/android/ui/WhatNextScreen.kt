package com.coucou.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coucou.android.R
import com.coucou.android.app.AppModel
import com.coucou.android.core.IconKind
import com.coucou.android.core.TypeScale

data class PullRequestItem(
    val number: Int,
    val title: String,
    val repo: String,
    val author: String,
    val branch: String,
    val isDraft: Boolean = false,
    val linkedConversationTitle: String? = null,
)

data class IssueItem(
    val number: Int,
    val title: String,
    val repo: String,
    val author: String,
    val labels: List<String> = emptyList(),
)

/**
 * What's Next screen:
 * - Pending Pull Requests needing review/merge.
 * - Assigned issues (@me) with a 1-touch "Resolver com Kraken" trigger.
 * - Zero authorization friction: purely autonomous triggers and live review.
 */
@Composable
fun WhatNextScreen(
    model: AppModel,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val t = tokens()
    var feedbackMessage by remember { mutableStateOf<String?>(null) }

    // Initial mock/cached state until synced from Kraken's githubProjectService
    val pullRequests = remember {
        listOf(
            PullRequestItem(
                number = 52,
                title = "feat(mobile): servidor do protocolo Coucou Phone Link",
                repo = "KrakenBridge-Web",
                author = "ApenasGabs",
                branch = "feat/#52-coucou-phone-link",
                linkedConversationTitle = "Monitoramento Mobile e What's Next",
            ),
            PullRequestItem(
                number = 53,
                title = "feat(pwa): transformar frontend em PWA com Web Push",
                repo = "KrakenBridge-Web",
                author = "ApenasGabs",
                branch = "feat/#53-kraken-pwa-push",
            ),
        )
    }

    val issues = remember {
        listOf(
            IssueItem(
                number = 1,
                title = "feat: bootstrap do Kraken Mobile com foco em notificações e What's Next",
                repo = "kraken-mobile",
                author = "ApenasGabs",
                labels = listOf("feat", "mobile", "android"),
            ),
            IssueItem(
                number = 50,
                title = "feat(chat): visualizador in-app de telemetria de agentes",
                repo = "KrakenBridge-Web",
                author = "ApenasGabs",
                labels = listOf("enhancement"),
            ),
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = stringResource(R.string.tab_what_next),
                    style = TypeScale.TITLE.style(t.text.c()).copy(fontSize = 26.sp, fontWeight = FontWeight.Bold),
                )
                Text(
                    text = "Acompanhamento de Demandas & PRs",
                    style = TypeScale.LABEL.style(t.textDim.c()),
                )
            }

            // Sync badge
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(t.panel.c())
                    .border(1.dp, t.line.c(), CircleShape)
                    .clickable(role = Role.Button) {
                        feedbackMessage = "Sincronizando com Kraken..."
                    }
                    .padding(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF22C55E)),
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Homelab gabisa",
                        style = TypeScale.LABEL.style(t.textDim.c()).copy(fontSize = 11.sp),
                    )
                }
            }
        }

        // Action feedback banner if triggered
        feedbackMessage?.let { msg ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E3A8A).copy(alpha = 0.5f))
                    .border(1.dp, Color(0xFF3B82F6), RoundedCornerShape(12.dp))
                    .padding(12.dp),
            ) {
                Text(
                    text = msg,
                    style = TypeScale.BODY.style(Color(0xFF93C5FD)),
                    fontSize = 13.sp,
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 8.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Section 1: Pull Requests
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp),
                ) {
                    Text(
                        text = stringResource(R.string.what_next_prs_title),
                        style = TypeScale.TITLE.style(t.text.c()).copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFF8B5CF6).copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                    ) {
                        Text(
                            text = pullRequests.size.toString(),
                            style = TypeScale.LABEL.style(Color(0xFFA78BFA)),
                            fontSize = 11.sp,
                        )
                    }
                }
            }

            items(pullRequests) { pr ->
                PullRequestCard(
                    pr = pr,
                    onApprove = {
                        feedbackMessage = "🐙 PR #${pr.number} aprovado com sucesso via smartphone!"
                    },
                    onRequestChanges = {
                        feedbackMessage = "🐙 Enviada instrução de ajuste para o Kraken no PR #${pr.number}."
                    },
                )
            }

            // Section 2: Issues
            item {
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp),
                ) {
                    Text(
                        text = stringResource(R.string.what_next_issues_title),
                        style = TypeScale.TITLE.style(t.text.c()).copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFF3B82F6).copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                    ) {
                        Text(
                            text = issues.size.toString(),
                            style = TypeScale.LABEL.style(Color(0xFF60A5FA)),
                            fontSize = 11.sp,
                        )
                    }
                }
            }

            items(issues) { issue ->
                IssueCard(
                    issue = issue,
                    onResolveWithKraken = {
                        feedbackMessage = "🐙 Kraken iniciado autonomamente para resolver a Issue #${issue.number}!"
                    },
                )
            }
        }
    }
}

@Composable
private fun PullRequestCard(
    pr: PullRequestItem,
    onApprove: () -> Unit,
    onRequestChanges: () -> Unit,
) {
    val t = tokens()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(t.panel.c())
            .border(1.dp, t.line.c(), RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Repo tag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(t.panel2.c())
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(
                        text = pr.repo,
                        style = TypeScale.LABEL.style(t.textDim.c()),
                        fontSize = 11.sp,
                    )
                }

                Text(
                    text = "#${pr.number}",
                    style = TypeScale.LABEL.style(Color(0xFFA78BFA)),
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = pr.title,
                style = TypeScale.BODY.style(t.text.c()).copy(fontWeight = FontWeight.Medium),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Branch: ${pr.branch} • Por ${pr.author}",
                style = TypeScale.LABEL.style(t.textDim.c()),
                fontSize = 12.sp,
            )

            pr.linkedConversationTitle?.let { convTitle ->
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "💬 Sessão: $convTitle",
                    style = TypeScale.LABEL.style(Color(0xFF38BDF8)),
                    fontSize = 11.sp,
                )
            }

            Spacer(Modifier.height(14.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                // Approve
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF16A34A).copy(alpha = 0.2f))
                        .border(1.dp, Color(0xFF22C55E), RoundedCornerShape(10.dp))
                        .clickable(role = Role.Button, onClick = onApprove)
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "✓ Aprovar PR",
                        style = TypeScale.LABEL.style(Color(0xFF4ADE80)),
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                // Request adjustment
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(t.panel2.c())
                        .border(1.dp, t.line.c(), RoundedCornerShape(10.dp))
                        .clickable(role = Role.Button, onClick = onRequestChanges)
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Pedir Ajuste",
                        style = TypeScale.LABEL.style(t.text.c()),
                    )
                }
            }
        }
    }
}

@Composable
private fun IssueCard(
    issue: IssueItem,
    onResolveWithKraken: () -> Unit,
) {
    val t = tokens()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(t.panel.c())
            .border(1.dp, t.line.c(), RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(t.panel2.c())
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text(
                        text = issue.repo,
                        style = TypeScale.LABEL.style(t.textDim.c()),
                        fontSize = 11.sp,
                    )
                }

                Text(
                    text = "#${issue.number}",
                    style = TypeScale.LABEL.style(Color(0xFF60A5FA)),
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = issue.title,
                style = TypeScale.BODY.style(t.text.c()).copy(fontWeight = FontWeight.Medium),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            if (issue.labels.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    issue.labels.forEach { label ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(t.panel2.c())
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                        ) {
                            Text(
                                text = label,
                                style = TypeScale.LABEL.style(t.textDim.c()),
                                fontSize = 10.sp,
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // 1-Touch autonomous trigger button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF2563EB))
                    .clickable(role = Role.Button, onClick = onResolveWithKraken)
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CoucouIcon(IconKind.SEND, tint = Color.White, size = 16.dp)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.what_next_resolve_btn),
                        style = TypeScale.LABEL.style(Color.White),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
