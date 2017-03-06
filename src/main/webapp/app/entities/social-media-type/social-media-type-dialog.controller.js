(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('SocialMediaTypeDialogController', SocialMediaTypeDialogController);

    SocialMediaTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SocialMediaType'];

    function SocialMediaTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SocialMediaType) {
        var vm = this;

        vm.socialMediaType = entity;
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
            if (vm.socialMediaType.id !== null) {
                SocialMediaType.update(vm.socialMediaType, onSaveSuccess, onSaveError);
            } else {
                SocialMediaType.save(vm.socialMediaType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:socialMediaTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
