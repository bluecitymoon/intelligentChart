(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonSocialMediaDialogController', PersonSocialMediaDialogController);

    PersonSocialMediaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonSocialMedia', 'Person', 'SocialMediaType', 'SocialMediaAttributeName'];

    function PersonSocialMediaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonSocialMedia, Person, SocialMediaType, SocialMediaAttributeName) {
        var vm = this;

        vm.personSocialMedia = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});
        vm.socialmediatypes = SocialMediaType.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});
        vm.socialmediaattributenames = SocialMediaAttributeName.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personSocialMedia.id !== null) {
                PersonSocialMedia.update(vm.personSocialMedia, onSaveSuccess, onSaveError);
            } else {
                PersonSocialMedia.save(vm.personSocialMedia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personSocialMediaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
