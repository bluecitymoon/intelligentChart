(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('SocialMediaAttributeNameDialogController', SocialMediaAttributeNameDialogController);

    SocialMediaAttributeNameDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SocialMediaAttributeName'];

    function SocialMediaAttributeNameDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SocialMediaAttributeName) {
        var vm = this;

        vm.socialMediaAttributeName = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.socialMediaAttributeName.id !== null) {
                SocialMediaAttributeName.update(vm.socialMediaAttributeName, onSaveSuccess, onSaveError);
            } else {
                SocialMediaAttributeName.save(vm.socialMediaAttributeName, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:socialMediaAttributeNameUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
