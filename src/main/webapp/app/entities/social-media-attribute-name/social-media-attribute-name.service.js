(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('SocialMediaAttributeName', SocialMediaAttributeName);

    SocialMediaAttributeName.$inject = ['$resource'];

    function SocialMediaAttributeName ($resource) {
        var resourceUrl =  'api/social-media-attribute-names/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
