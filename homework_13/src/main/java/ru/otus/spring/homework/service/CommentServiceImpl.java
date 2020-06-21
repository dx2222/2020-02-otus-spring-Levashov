package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.dto.Role;
import ru.otus.spring.homework.repository.CommentRepositoryJpa;
import ru.otus.spring.homework.domain.Comment;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepositoryJpa commentRepository;

    private final MutableAclService mutableAclService;

    @Autowired
    public CommentServiceImpl(CommentRepositoryJpa commentRepository, MutableAclService mutableAclService) {
        this.commentRepository  = commentRepository;
        this.mutableAclService   = mutableAclService;
    }

    @PreAuthorize("not hasRole("+(char) 39+ Role.ROLE_ADMIN+(char) 39+ ")")
    public void insertComment(Long bookid, String comment) {
        Comment newComment = commentRepository.save(Comment.builder().comment(comment).bookID(bookid).build());

        final Sid owner = new PrincipalSid( SecurityContextHolder.getContext().getAuthentication() );

        MutableAcl acl = mutableAclService.createAcl(new ObjectIdentityImpl(newComment.getClass(), newComment.getId()));
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, owner, true);
        mutableAclService.updateAcl(acl);
    }


    public void deleteComment(Long id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Comment> selectCommentByBookID(Long bookid) {
      return  commentRepository.findByBookID(bookid);
    }
}
