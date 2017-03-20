(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('SocialMediaAttributeNameDeleteController',SocialMediaAttributeNameDeleteController);

    SocialMediaAttributeNameDeleteController.$inject = ['$uibModalInstance', 'entity', 'SocialMediaAttributeName'];

    function SocialMediaAttributeNameDeleteController($uibModalInstance, entity, SocialMediaAttributeName) {
        var vm = this;

        vm.socialMediaAttributeName = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SocialMediaAttributeName.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
