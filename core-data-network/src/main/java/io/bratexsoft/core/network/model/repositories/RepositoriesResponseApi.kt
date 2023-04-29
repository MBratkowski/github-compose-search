package io.bratexsoft.core.network.model.repositories


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import io.bratexsoft.core.data.api.model.License
import io.bratexsoft.core.data.api.model.Repositories

data class RepositoriesResponseApi(
    @SerializedName("allow_forking")
    val allowForking: Boolean,
    @SerializedName("archive_url")
    val archiveUrl: String,
    @SerializedName("archived")
    val archived: Boolean,
    @SerializedName("assignees_url")
    val assigneesUrl: String,
    @SerializedName("blobs_url")
    val blobsUrl: String,
    @SerializedName("branches_url")
    val branchesUrl: String,
    @SerializedName("clone_url")
    val cloneUrl: String,
    @SerializedName("collaborators_url")
    val collaboratorsUrl: String,
    @SerializedName("comments_url")
    val commentsUrl: String,
    @SerializedName("commits_url")
    val commitsUrl: String,
    @SerializedName("compare_url")
    val compareUrl: String,
    @SerializedName("contents_url")
    val contentsUrl: String,
    @SerializedName("contributors_url")
    val contributorsUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("default_branch")
    val defaultBranch: String,
    @SerializedName("deployments_url")
    val deploymentsUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("disabled")
    val disabled: Boolean,
    @SerializedName("downloads_url")
    val downloadsUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("fork")
    val fork: Boolean,
    @SerializedName("forks")
    val forks: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("forks_url")
    val forksUrl: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("git_commits_url")
    val gitCommitsUrl: String,
    @SerializedName("git_refs_url")
    val gitRefsUrl: String,
    @SerializedName("git_tags_url")
    val gitTagsUrl: String,
    @SerializedName("git_url")
    val gitUrl: String,
    @SerializedName("has_downloads")
    val hasDownloads: Boolean,
    @SerializedName("has_issues")
    val hasIssues: Boolean,
    @SerializedName("has_pages")
    val hasPages: Boolean,
    @SerializedName("has_projects")
    val hasProjects: Boolean,
    @SerializedName("has_wiki")
    val hasWiki: Boolean,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("hooks_url")
    val hooksUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_template")
    val isTemplate: Boolean,
    @SerializedName("issue_comment_url")
    val issueCommentUrl: String,
    @SerializedName("issue_events_url")
    val issueEventsUrl: String,
    @SerializedName("issues_url")
    val issuesUrl: String,
    @SerializedName("keys_url")
    val keysUrl: String,
    @SerializedName("labels_url")
    val labelsUrl: String,
    @SerializedName("language")
    val language: String?,
    @SerializedName("languages_url")
    val languagesUrl: String,
    @SerializedName("license")
    val license: LicenseResponseApi?,
    @SerializedName("merges_url")
    val mergesUrl: String,
    @SerializedName("milestones_url")
    val milestonesUrl: String,
    @SerializedName("mirror_url")
    val mirrorUrl: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("network_count")
    val networkCount: Int,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("notifications_url")
    val notificationsUrl: String,
    @SerializedName("open_issues")
    val openIssues: Int,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    @SerializedName("owner")
    val owner: OwnerResponseApi,
    @SerializedName("permissions")
    val permissions: PermissionsResponseApi,
    @SerializedName("pulls_url")
    val pullsUrl: String,
    @SerializedName("pushed_at")
    val pushedAt: String,
    @SerializedName("releases_url")
    val releasesUrl: String,
    @SerializedName("size")
    val size: Int,
    @SerializedName("ssh_url")
    val sshUrl: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("stargazers_url")
    val stargazersUrl: String,
    @SerializedName("statuses_url")
    val statusesUrl: String,
    @SerializedName("subscribers_count")
    val subscribersCount: Int,
    @SerializedName("subscribers_url")
    val subscribersUrl: String,
    @SerializedName("subscription_url")
    val subscriptionUrl: String,
    @SerializedName("svn_url")
    val svnUrl: String,
    @SerializedName("tags_url")
    val tagsUrl: String,
    @SerializedName("teams_url")
    val teamsUrl: String,
    @SerializedName("temp_clone_token")
    val tempCloneToken: String,
    @SerializedName("topics")
    val topics: List<String>,
    @SerializedName("trees_url")
    val treesUrl: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("visibility")
    val visibility: String,
    @SerializedName("watchers")
    val watchers: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int,
    @SerializedName("web_commit_signoff_required")
    val webCommitSignoffRequired: Boolean
)

@SuppressWarnings("LongMethod")
fun RepositoriesResponseApi.toDomain() = Repositories(
    allowForking = allowForking,
    archiveUrl = archiveUrl,
    archived = archived,
    assigneesUrl = assigneesUrl,
    blobsUrl = blobsUrl,
    branchesUrl = branchesUrl,
    cloneUrl = cloneUrl,
    collaboratorsUrl = collaboratorsUrl,
    commentsUrl = commentsUrl,
    commitsUrl = commitsUrl,
    compareUrl = compareUrl,
    contentsUrl = contentsUrl,
    contributorsUrl = contributorsUrl,
    createdAt = createdAt,
    defaultBranch = defaultBranch,
    deploymentsUrl = deploymentsUrl,
    description = description,
    disabled = disabled,
    downloadsUrl = downloadsUrl,
    eventsUrl = eventsUrl,
    fork = fork,
    forks = forks,
    forksCount = forksCount,
    forksUrl = forksUrl,
    fullName = fullName,
    gitCommitsUrl = gitCommitsUrl,
    gitRefsUrl = gitRefsUrl,
    gitTagsUrl = gitTagsUrl,
    gitUrl = gitUrl,
    hasDownloads = hasDownloads,
    hasIssues = hasIssues,
    hasPages = hasPages,
    hasProjects = hasProjects,
    hasWiki = hasWiki,
    homepage = homepage ?: "",
    hooksUrl = hooksUrl,
    htmlUrl = htmlUrl,
    id = id,
    isTemplate = isTemplate,
    issueCommentUrl = issueCommentUrl,
    issueEventsUrl = issueEventsUrl,
    issuesUrl = issuesUrl,
    keysUrl = keysUrl,
    labelsUrl = labelsUrl,
    language = language ?: "",
    languagesUrl = languagesUrl,
    license = license?.toDomain()?: License(),
    mergesUrl = mergesUrl,
    milestonesUrl = milestonesUrl,
    mirrorUrl = mirrorUrl ?: "",
    name = name,
    networkCount = networkCount,
    nodeId = nodeId,
    notificationsUrl = notificationsUrl,
    openIssues = openIssues,
    owner = owner.toDomain(),
    openIssuesCount = openIssuesCount,
    permissions = permissions.toDomain(),
    pullsUrl = pullsUrl,
    pushedAt = pushedAt,
    releasesUrl = releasesUrl,
    subscribersCount = subscribersCount,
    subscribersUrl = subscribersUrl,
    size = size,
    sshUrl = sshUrl,
    stargazersCount = stargazersCount,
    stargazersUrl = stargazersUrl,
    statusesUrl = statusesUrl,
    subscriptionUrl = subscriptionUrl,
    svnUrl = svnUrl,
    tagsUrl = tagsUrl,
    teamsUrl = teamsUrl,
    tempCloneToken = tempCloneToken,
    topics = topics,
    treesUrl = treesUrl,
    updatedAt = updatedAt,
    url = url,
    visibility = visibility,
    watchers = watchers,
    watchersCount = watchersCount,
    webCommitSignoffRequired = webCommitSignoffRequired
)
